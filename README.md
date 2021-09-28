# 1 Project info

This is a Receipt Generator project written in Java.

---

# 2 How to build

Make sure you have the latest Maven binaries and the `mvn` path variable configured.

Run:

```bash
mvn clean package
```

Unit tests are automatically run.

The artifact is located at `target/pinnacle-coding-test-1.0.0.jar`.

---

# 3 How to run

Run:

```bash
java -jar -DinputFile=path/to/useCase1.json pinnacle-coding-test-1.0.0.jar
java -jar -DinputFile=path/to/useCase2.json pinnacle-coding-test-1.0.0.jar
java -jar -DinputFile=path/to/useCase3.json pinnacle-coding-test-1.0.0.jar
```

The output file is located inside the `output` folder.
