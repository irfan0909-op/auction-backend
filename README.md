# BidHive backend (Spring Boot + MySQL)

Requirements: Java 17+, Maven 3.8+, MySQL 8+.

1. Create the database (or let the JDBC URL create it):
   `CREATE DATABASE auction_db;`
2. Configure credentials with environment variables `DB_USERNAME` and `DB_PASSWORD`, or edit `src/main/resources/application.properties`.
3. From this `backend` folder run: `mvn spring-boot:run`
4. Check `http://localhost:8080/api/health`.

## API endpoints
- `GET /api/health`
- `GET /api/auctions` (optional `?status=ACTIVE`)
- `GET /api/auctions/{id}`
- `POST /api/auctions`
- `GET /api/auctions/{id}/bids`
- `POST /api/auctions/{id}/bids`

Example create-auction JSON:
```json
{
  "title": "Vintage camera",
  "description": "Working camera in good condition",
  "category": "Electronics",
  "imageUrl": "https://example.com/camera.jpg",
  "startingPrice": 100.00,
  "endTime": "2027-01-01T18:00:00",
  "sellerName": "demo-seller"
}
```
Example bid JSON:
```json
{"bidderName":"demo-buyer","amount":125.00}
```

This is a development starter, not production-ready authentication. Seller/bidder names are supplied in request bodies, so add authenticated user identity and database-level locking before deployment. Bid endpoints validate the minimum increment and auction end time.
