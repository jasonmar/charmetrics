## Spray / Akka JSON API Exercise

Request:

```
{ 
  "id" : Long,
  "payload" : String,
  "time" : Long
}
```

Response:

```
{
  "most_frequent_char" : String,
  "least_frequent_char" : String,
  “ratio_most_used_char_per_word" : Float
}
```

## Notes:
Uppercase letters are converted to lowercase.
Only the character with lowest integer id is returned in case of multiple characters with the same frequency.


## Starting the service:

```
sbt run
```



