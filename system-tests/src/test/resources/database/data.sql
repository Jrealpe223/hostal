TRUNCATE TABLE common.companies;

INSERT INTO common.type_categories (tyca_name, tyca_slug,tyca_status, tyca_can_edit )
VALUES ('test category', 'test_category',true,true);

INSERT INTO common.types (tyca_id, type_name)
VALUES ((select tyca_id from common.type_categories where tyca_slug='test_category'), 'test');

INSERT INTO common.companies (comp_type, comp_slug, comp_schema, comp_name, comp_creation )
VALUES ((select type_id from common.types where type_name='test'), 'test', 'test', 'test', now());
