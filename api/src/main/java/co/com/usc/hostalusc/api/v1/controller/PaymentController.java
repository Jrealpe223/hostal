package co.com.usc.hostalusc.api.v1.controller;


import co.com.usc.hostalusc.api.utils.ResponseUtils;
import co.com.usc.hostalusc.domain.contracts.MessageCode;
import co.com.usc.hostalusc.domain.contracts.OutputContract;
import co.com.usc.hostalusc.domain.service.PaymentService;
import co.com.usc.hostalusc.repository.model.common.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/payments")
@Tag(name = "Payment Controller")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get all payments")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getAllPayments(Pageable pageable) {
        log.info("Fetching all payments with pagination");
        try {
            Page<Payment> payments = paymentService.getAllPayments(pageable);
            return ResponseUtils.buildOutputContractSuccessResponse(payments.getContent(), HttpStatus.OK.value(), payments.getTotalElements());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getAllPayments() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Get payment by ID")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> getPaymentById(@PathVariable("id") Long id) {
        log.info("Fetching payment with ID: {}", id);
        try {
            Payment payment = paymentService.getPaymentById(id);
            return ResponseUtils.buildOutputContractSuccessResponse(payment, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error getPaymentById() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Create new payment")
    @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> createPayment(@RequestBody Payment payment) {
        log.info("Creating new payment with details: {}", payment);
        try {
            Payment createdPayment = paymentService.createPayment(payment);
            return ResponseUtils.buildOutputContractSuccessResponse(createdPayment, HttpStatus.CREATED.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error createPayment() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(summary = "Update payment")
    @PutMapping(value = "/update/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputContract<Object>> updatePayment(@PathVariable("id") Long id, @RequestBody Payment payment) {
        log.info("Updating payment with ID: {}", id);
        try {
            Payment updatedPayment = paymentService.updatePayment(id, payment);
            return ResponseUtils.buildOutputContractSuccessResponse(updatedPayment, HttpStatus.OK.value());
        } catch (Exception ex) {
            return ResponseUtils.buildOutputContractErrorResponse("Error updatePayment() -> " + ex.getMessage(), MessageCode.INTERVAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}