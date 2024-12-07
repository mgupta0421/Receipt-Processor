# Receipt-Processor
Receipt Points Calculator

A Spring Boot REST API for processing receipts and calculating reward points based on predefined rules. This project demonstrates a lightweight, in-memory solution for receipt processing with support for Docker containerization.

**Features:**
- Process receipts and assign unique IDs.
- Calculate points for each receipt based on rules like retailer name, total amount, purchase time, etc.
- RESTful endpoints for easy integration.
- Docker support for deployment.
- In-memory data storage (no persistence required).


**Steps to run application:**
This application processes receipts to calculate points based on predefined rules.

## Prerequisites
- Docker installed on your machine.

## Steps to Build and Run

### 1. Clone the Repository
Clone the repository to your local machine:
```bash
git clone https://github.com/mgupta0421/Receipt-Processor.git
cd receipt-processor

### 2. Build the docker file
docker build -t receipt-processor .

### 3. Run the application
docker run -p 8080:8080 receipt-processor



