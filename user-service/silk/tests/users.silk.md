# Get all users

This can be run with `silk -silk.url="http://localhost:9001/"`

## `GET users`

Perform a find all users information with default parameters.

===

* `Status: 200`
* `Content-Type: "application/json;charset=UTF-8"`
```json
[
  {
    "id": 1,
    "firstname": "Sean",
    "lastname": "Louis"
  },
  {
    "id": 2,
    "firstname": "Lori",
    "lastname": "Betty"
  },
  {
    "id": 3,
    "firstname": "Bruce",
    "lastname": "Bruce"
  },
  {
    "id": 4,
    "firstname": "Nancy",
    "lastname": "Jose"
  },
  {
    "id": 5,
    "firstname": "Keith",
    "lastname": "Daniel"
  },
  {
    "id": 6,
    "firstname": "Norma",
    "lastname": "Julia"
  },
  {
    "id": 7,
    "firstname": "Daniel",
    "lastname": "Wanda"
  },
  {
    "id": 8,
    "firstname": "Doris",
    "lastname": "Lisa"
  },
  {
    "id": 9,
    "firstname": "Frances",
    "lastname": "Wayne"
  },
  {
    "id": 10,
    "firstname": "Anthony",
    "lastname": "Juan"
  }
]
```

## `GET users?page=3&item_per_page=2`

Perform a find all users information at page `3` and `2` item2 per page.

===

* `Status: 200`
* `Content-Type: "application/json;charset=UTF-8"`
```json
[
  {
    "id": 5,
    "firstname": "Keith",
    "lastname": "Daniel"
  },
  {
    "id": 6,
    "firstname": "Norma",
    "lastname": "Julia"
  }
]
```

## `GET users?page=-10&item_per_page=2`

Perform a find all users information with invalid page arguments.

===

* `Status: 400`

## `GET users?page=999&item_per_page=10`

Perform a find all users information with invalid page arguments.

===

* `Status: 400`

## `GET users?page=4&item_per_page=-3`

Perform a find all users information with invalid item_per_page arguments.

===

* `Status: 400`
