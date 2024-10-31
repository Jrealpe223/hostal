CREATE TABLE IF NOT EXISTS event_type
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(20) NOT NULL UNIQUE,
    created TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS product_created_events
(
    id              BIGSERIAL PRIMARY KEY,
    job_uuid        VARCHAR(36) NOT NULL,
    event_type_id   INTEGER     NOT NULL,
    event_at        TIMESTAMP   NOT NULL,
    product_id      INTEGER     NOT NULL,
    created         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    additional_info JSONB        NULL
);


CREATE OR REPLACE FUNCTION insert_product_created_events_func(job_uuid_input CHARACTER VARYING(36),
                                                              event_type_id_input INT,
                                                              event_at_input TIMESTAMP WITH TIME ZONE,
                                                              product_id_input BIGINT,
                                                              additional_info_input JSONB) RETURNS INTEGER
    LANGUAGE plpgsql
AS
'
DECLARE
    events_count                            INTEGER;
    total_in_last_10_minutes                INTEGER;
    insert_result                           INTEGER DEFAULT 0;
    DECLARE EVENT_TYPE_ID_STARTED  CONSTANT integer DEFAULT 100;
    DECLARE EVENT_TYPE_ID_FAILED   CONSTANT integer DEFAULT 200;
    DECLARE EVENT_TYPE_ID_RETRYING CONSTANT integer DEFAULT 300;
    DECLARE EVENT_TYPE_ID_FINISHED CONSTANT integer DEFAULT 400;
BEGIN

    SELECT count(*)
    INTO events_count
    FROM product_created_events
    WHERE job_uuid = job_uuid_input
      AND event_type_id = event_type_id_input;

    IF events_count > 0 THEN
        IF event_type_id_input = EVENT_TYPE_ID_STARTED THEN
            RAISE EXCEPTION SQLSTATE ''90100'' USING MESSAGE = ''STARTED_ALREADY_EXISTS'';
        ELSIF event_type_id_input = EVENT_TYPE_ID_FINISHED THEN
            RAISE EXCEPTION SQLSTATE ''90400'' USING MESSAGE = ''FINISHED_ALREADY_EXISTS'';
        ELSIF event_type_id_input = EVENT_TYPE_ID_RETRYING OR event_type_id_input = EVENT_TYPE_ID_FAILED THEN
            select count(*)
            INTO total_in_last_10_minutes
            from product_created_events
            where job_uuid = job_uuid_input
              AND event_type_id = event_type_id_input
              AND created >= (CURRENT_TIMESTAMP - INTERVAL ''10 min'')
            GROUP BY job_uuid, event_type_id;

            IF total_in_last_10_minutes >= 10 THEN
                IF event_type_id_input = EVENT_TYPE_ID_FAILED THEN
                    RAISE EXCEPTION SQLSTATE ''90200'' USING MESSAGE = ''FAILED_LIMIT_EXCEEDED'';
                END IF;
                IF event_type_id_input = EVENT_TYPE_ID_RETRYING THEN
                    RAISE EXCEPTION SQLSTATE ''90300'' USING MESSAGE = ''RETRYING_LIMIT_EXCEEDED'';
                END IF;
            END IF;
        ELSE
            RAISE EXCEPTION SQLSTATE ''90000'' USING MESSAGE = ''UNKNOWN_OPTION'';
        END IF;
    END IF;

    INSERT INTO product_created_events(job_uuid, event_type_id, event_at, product_id, created,
                                                 additional_info)
    VALUES (job_uuid_input,
            event_type_id_input,
            event_at_input,
            product_id_input,
            CURRENT_TIMESTAMP,
            additional_info_input)
    RETURNING id INTO insert_result;

    RETURN insert_result;
END;
';
