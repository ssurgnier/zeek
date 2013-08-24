# zeek [![Build Status](https://secure.travis-ci.org/ssurgnier/zeek.png?branch=master)](http://travis-ci.org/ssurgnier/zeek)

A simple zookeeper cli.

## Usage
```
lein run path/to/config.clj prod

localhost@prod:/ $ ls
(znode1 znode2 znode3)
localhost@prod:/ $ cd znode1
localhost@prod:/znode1 $ get
#<byte[] [B@727f3b8a>
localhost@prod:/znode1 $ cd ..
localhost@prod:/ $ rm znode1
Removed /znode1
localhost@prod:/ $
```

## License

Copyright © 2013 Steven Surgnier

Distributed under the Eclipse Public License, the same as Clojure.
