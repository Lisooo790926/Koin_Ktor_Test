### Transaction Testing
1. Clone the project
2. `cd ./db` and `docker compose up -d` (init the basic db)
3. Run the project
4. Hit `curl --location --request GET 'http://localhost:8080/select-count'`
5. Check above result, should start from 1 (because only 1 data)
6. Hit `curl --location --request GET 'http://localhost:8080/dao-database/use-transaction-to-insert-data'`
7. Then repeat step 4 and check the result, now it will increase to 4 (ideally it should be 1, but it will create new connection to insert data)
8. curl --location --request GET 'http://localhost:8080/same-database/use-transaction-to-insert-data'
9. Then repeat step 4 and check the result, now it keep in 4 (This behavior is expected)

### Conclusion
If we use useTransaction, we should make sure the database coming from the same object, if we use another injection, it will lost the atomic
