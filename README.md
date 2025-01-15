# Project: Vehicle Management System

## Project Description
The Vehicle Management System is designed to manage vehicle-related data efficiently. It provides features like data import/export, CRUD operations, search functionality, and server-side pagination. The project leverages a React frontend and a Node.js backend integrated with Bull.js for batch processing and Redis for queue management. Real-time notifications are implemented using WebSockets.

## Features

### Data Import
- Import data from Excel and CSV files as a batch job using Bull.js.
- Automatically calculate the `age_of_vehicle` based on the `manufactured_date` during the import.
- Fields included: `id`, `first_name`, `last_name`, `email`, `car_make`, `car_model`, `vin`, `manufactured_date`, and `age_of_vehicle`.
- Save imported data into the database using TypeORM.

### CRUD Operations
- Create, Update, and Delete functionalities for vehicle records.

### Listing and Pagination
- Endpoint to list all records sorted by `manufactured_date` in ascending order.
- Server-side pagination implemented with 100 records per page.

### Search Feature
- Wildcard search enabled on the `car_model` field (e.g., "M*" returns "Mitsubishi" and "MDM").

### Data Export
- Batch process to export data to a CSV file based on criteria (e.g., cars older than 5 years).
- Real-time notifications to the UI when the batch job is complete.

## Setup Instructions

### Prerequisites
- Node.js (v14 or above)
- MySQL 
- npm

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd vehicle-management-system
   ```

2. Install dependencies for the backend and frontend:
   ```bash
   cd backend
   npm install
   cd ../frontend
   npm install
   ```

3. Configure environment variables:
   - Backend: Create a `.env` file in the `backend` directory with the following:
     ```env
     DATABASE_HOST=localhost
     DATABASE_PORT=3306
     DATABASE_USER=root
     DATABASE_PASSWORD=yourpassword
     DATABASE_NAME=vehicles
     REDIS_HOST=localhost
     REDIS_PORT=6379
     ```
   - Frontend: Update the API URL in the frontend `.env` file if necessary.

4. Start Redis:
   ```bash
   redis-server
   ```

5. Run the backend:
   ```bash
   cd backend
   npm start
   ```

6. Run the frontend:
   ```bash
   cd frontend
   npm start
   ```

### Usage
1. Access the application at `http://localhost:8080`.
2. Use the file upload feature to import vehicle data.
3. Perform CRUD operations, search, and export as needed.
4. Monitor real-time notifications for batch job completion.

## Important Information
- The batch processing jobs are managed using Bull.js and require a running Redis server.
- Data import/export adheres to the specified format for smooth operation.
- Make sure the database is properly configured and migrated before running the application.

### Database Migration
Run the following command to migrate the database:
```bash
cd backend
npm run typeorm migration:run
```

## Contact
For any issues or inquiries, please contact the repository maintainer at [your-email@example.com].

