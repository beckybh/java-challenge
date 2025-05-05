# java-challenge

# Coffee Store Exercise

## What is a coffee?

Is the minimal unit to sell in our store, have fields like:

- id (Could be Long or UUID)
- name (Short text, not more than 200)
- description (Long text)
- enabled (Boolean)

## What is an Order?

Is an action to sell coffees to a client

- id (Could be Long or UUID)
- description (Long text)
- List of coffees (List or Set)
- Total Items (Integer)
- Total (BigDecimal or Double)
- Order Date (LocalDateTime)

## What is an additional?

Is an extra thing to put in your coffee

- id (Could be Long or UUID)
- description (Long text)
- Cost (BigDecimal or Double)
- Combine Cost (BigDecimal or Double)

## Specifications

- Register new Coffees
- Show all coffees
- Search coffees by name or description
- Register prices keeping a history
- Combine Coffees with additionals
- Crete and order
- Search orders by ID and coffees

### Combination algorithm

When we combine a coffee with extras, we must calculate the price based on a percentage of the extras as the case may be, because each extra has a relative cost.

```
# Extra or additionals
- Vanilla cost = 3, Add 100% of the cost
- Creamer cost = 5, Only Creamer add the cost, but is combine, add 80% of the cost

- Mochaccino Coffee cost = 2
```

## Solution

The Coffee cost is the base (Base price = 2) and need to add Vanilla and creamer cost using the formula

```
Result = 2 + (3) + (5 * 0.8) = 9
```

The name of the combined coffee is generated using extra adds, where the order is given by the price from highest to lowest.

```
Name = Creamer Vanilla Coffee
```

If you found 2 or more extras with the same price, you have to order using the name
