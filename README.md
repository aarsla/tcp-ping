# Installation

```
mvn package
```
# Help
```
java -jar target/tcp-ping-0.0.1-SNAPSHOT.jar -help
```
# Options

| Option | Description |
| ------ | ------ |
| --catcher, -c | Catcher mode, Default: false |
| --pitcher, -p | Pitcher mode, Default: false |
| --port, -port | TCP socket port, Default: 9900 |
| --bind, -bind | Binding IP address |
| --help, -help | Display help |
| --mps, -mps | Messages per second, Default: 1 |
| --size, -size | Message size in bytes, Default: 300 |

# Example use

### Catcher
```
java -jar target/tcp-ping-0.0.1-SNAPSHOT.jar -c -bind <IP ADDRESS>
```
![Screenshot](https://raw.githubusercontent.com/aarsla/tcp-ping/master/catcher.png)

### Pitcher
```
java -jar target/tcp-ping-0.0.1-SNAPSHOT.jar -p <CATCHER IP ADDRESS>
```
![Screenshot](https://raw.githubusercontent.com/aarsla/tcp-ping/master/pitcher.png)
