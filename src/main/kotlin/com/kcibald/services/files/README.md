# Files token definition
## This file defines how file tokens is formatted
A general structure of an internal file token format is like following:
```
v{version-number}:{token}
```
version number can be any numerical integers, while the token section can be a sequence of any ASCII characters of any length.

Example of such token:
```
v1:abcdefg123
```

## Version 1 file token specification
Following the general format, the token should be formatted like this
```
v1:d{domain-number}-{token}
```
The domain-number refers to a specific domain. Assert d0 is `cdn.kcibald.com`, the token 
`v1:d0-abcdefg` should be parsed as `cdn.kcibald.com/v1-abcdefg`