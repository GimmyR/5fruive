DROP TABLE IF EXISTS most_purchased;

CREATE OR REPLACE VIEW most_purchased AS
	SELECT
        pr.id,
	    pr.name,
	    pu.amount
	   FROM product pr,
	    ( SELECT purchase_detail.product_id,
	            round((sum(purchase_detail.amount))::numeric, 2) AS amount
	           FROM purchase_detail
	          GROUP BY purchase_detail.product_id) pu
	  WHERE (pr.id = pu.product_id)
	  ORDER BY pu.amount DESC
	 LIMIT 7;