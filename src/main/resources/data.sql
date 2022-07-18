CREATE MATERIALIZED VIEW simple_search AS
SELECT i.id ::bigint         AS id,
        i.name ::text         AS name,
        'ITEM' ::varchar      AS table_type,
        i.creation_date::timestamp AS c_date,
        to_tsvector(i.name)   AS doc
from item i
UNION ALL
SELECT c.id ::bigint                                                                     AS id,
        c.name ::text                                                                     AS name,
        'COLLECTION' ::varchar                                                            AS table_type,
        c.creation_date::timestamp                                                             AS c_date,
            setweight(to_tsvector(c.name), 'A') || setweight(to_tsvector(c.description), 'B') AS doc
from collection c
UNION ALL
SELECT com.item_id ::bigint    AS id,
        com.text ::text         AS name,
        'COMMENT' ::varchar     AS table_type,
        com.creation_date::timestamp AS c_date,
        to_tsvector(com.text)   AS doc
from comment com;

CREATE INDEX idx_fts ON simple_search USING gin (doc);
CREATE UNIQUE INDEX idx_unq ON simple_search (id, table_type, c_date);


CREATE OR REPLACE FUNCTION refresh_view()
    RETURNS TRIGGER AS
$$
BEGIN
    REFRESH MATERIALIZED VIEW CONCURRENTLY simple_search;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER item_trigger_update_view
    AFTER INSERT OR UPDATE OR DELETE
                    ON item
                        FOR EACH ROW
                        EXECUTE PROCEDURE refresh_view();

CREATE TRIGGER collection_trigger_update_view
    AFTER INSERT OR UPDATE OR DELETE
                    ON collection
                        FOR EACH ROW
                        EXECUTE PROCEDURE refresh_view();

CREATE TRIGGER comment_trigger_update_view
    AFTER INSERT OR UPDATE OR DELETE
                    ON comment
                        FOR EACH ROW
                        EXECUTE PROCEDURE refresh_view();


CREATE OR REPLACE FUNCTION full_text_search(search_text TEXT)
    RETURNS TABLE
            (
                id         bigint,
                name       text,
                table_type varchar
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
RETURN QUERY
SELECT s.id, s.name, s.table_type FROM simple_search s WHERE s.doc @@ plainto_tsquery(search_text);
END;
$$

select *
from full_text_search('book:*');