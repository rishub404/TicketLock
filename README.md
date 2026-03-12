## SYSTEM OVERVIEW
**TicketLock** is a production-grade, distributed ticketing engine engineered to handle extreme concurrency. The primary directive is the absolute prevention of race conditions, double-bookings, and state inconsistencies during high-traffic booking windows.

It achieves this through a robust distributed locking mechanism utilizing the Redlock algorithm, ensuring that once a transaction is initiated, the asset is exclusively isolated until commit or timeout.

---

## ARCHITECTURE & STACK
* **Backend:** Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security (Maven)
* **State (Source of Truth):** PostgreSQL 15 (ACID-compliant relational state)
* **Distributed Lock Coordinator:** Redis 7 via Redisson
* **Frontend:** Next.js (App Router), TypeScript, Tailwind CSS
* **Infrastructure:** Docker & Docker Compose (Containerized local topology)

---

## THE CORE ENGINEERING DIRECTIVE
The system is designed to seamlessly process simultaneous requests for the exact same `ticket_id` by enforcing the following sequence:

1. **[REQ_INTERCEPT]** Spring Boot API receives concurrent booking requests.
2. **[LOCK_ACQUIRE]** The system attempts to acquire a distributed, mutually exclusive lock via Redisson for the targeted `ticket_id`.
3. **[STATE_VERIFY]** If the lock is successfully acquired, PostgreSQL is queried to ensure the ticket is still available.
4. **[EXECUTE_TX]** A database transaction is committed, changing the ticket state.
5. **[LOCK_RELEASE]** The Redis lock is yielded.
6. **[FALLBACK]** If the lock cannot be acquired, the system fails fast (or queues), returning an appropriate HTTP status to the Next.js client to prevent system drag.

---

## SYSTEM INITIALIZATION

### Prerequisites
* JDK 17
* Node.js & npm (for Next.js)
* Docker & Docker Compose

### Boot Sequence
1. **Clone the repository:**
   ```bash
   git clone https://github.com/rishub404/TicketLock.git
   cd TicketLock
   ```

2. **Spin up Infrastructure (PostgreSQL & Redis):**
   ```bash
   docker-compose up -d
   ```

3. **Initialize Backend:**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

4. **Initialize Frontend:**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

---

### DEMO