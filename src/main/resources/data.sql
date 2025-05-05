INSERT INTO coffees (id, name, description, cost, enabled)
VALUES (1, 'Mochaccino', 'Mochaccino coffee', 2, true );

INSERT INTO coffees (id, name, description, cost, enabled)
VALUES (2, 'Espresso', 'Espresso coffee', 1, true );

INSERT INTO coffees (id, name, description, cost, enabled)
VALUES (3, 'Macchiato', 'Macchiato coffee', 3, true );

INSERT INTO orders (id, description, order_date, total, total_items)
VALUES (1, 'First order', '2025-05-01T18:00:00', 3, 2);

INSERT INTO orders_coffees (order_entity_id, coffees_id)
VALUES (1, 1);

INSERT INTO orders_coffees (order_entity_id, coffees_id)
VALUES (1, 2);

INSERT INTO additionals (id, description, cost, combine_cost_percentage)
VALUES (1, 'Vanilla', 3, 100);

INSERT INTO additionals (id, description, cost, combine_cost_percentage)
VALUES (2, 'Creamer', 5, 80);