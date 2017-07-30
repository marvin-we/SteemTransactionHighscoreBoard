# SteemTransactionHighscoreBoard
A dockerized Java Application showing a highscore board for the number of processed operations in the last 24 hours

![Screenshot](http://i.imgur.com/68JBPp1.png)

This project has been developed by [@dez1337](http://www.steemit.com/@dez1337) and is based on the initial idea and concept of [@samupha](http://www.steemit.com/@samupha).

# How to build the project
To project is using the Build Management tool Maven and can by build by executing the command below:

```Shell
mvn clean install
```

# How to run the project
After building the binaries you can use docker to run them. Simply start the provided docker-compose file by running:

```Shell
docker-compose up -d
```
