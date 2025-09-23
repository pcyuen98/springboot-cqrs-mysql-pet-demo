# CQRS Pattern

Command Query Responsibility Segregation (CQRS) is a design pattern that separates **read** and **write** operations for a data store into distinct data models. This approach allows each model to be optimized independently, enhancing performance, scalability, and security.

---

## Context and Problem

In traditional architectures, a single data model is often used for both read and write operations. This approach is straightforward and suitable for basic CRUD (Create, Read, Update, Delete) operations.

<p align="center">
<img src="https://learn.microsoft.com/en-us/azure/architecture/patterns/_images/command-and-query-responsibility-segregation-cqrs-tradition-crud.png" alt="Traditional CRUD Architecture" width="460" height="350">
</p>

As applications grow, optimizing read and write operations on a single data model becomes challenging. Read and write operations often have different performance and scaling requirements. A traditional CRUD architecture doesn't account for this asymmetry, leading to several challenges:

- **Data Mismatch:** Read and write representations of data often differ. Some fields required during updates might be unnecessary during read operations.
- **Lock Contention:** Parallel operations on the same data set can cause lock contention.
- **Performance Issues:** The traditional approach can negatively affect performance due to load on the data store and complex queries required to retrieve information.
- **Security Challenges:** Managing security is difficult when entities are subject to both read and write operations, potentially exposing data in unintended contexts.
- **Complex Models:** Combining these responsibilities can result in an overly complicated model.

---

## Solution

The CQRS pattern addresses these issues by separating **write operations (commands)** from **read operations (queries):**

- **Commands** update data.
- **Queries** retrieve data.

This separation is beneficial in scenarios requiring a clear distinction between commands and reads.

### Understand Commands

Commands capture the user's intent and align with business processes. To ensure commands succeed, consider refining client-side validation, server-side logic, and asynchronous processing.

| Area of Refinement      | Recommendation |
|-------------------------|----------------|
| Client-side validation  | Validate conditions before sending commands to prevent obvious failures. For example, if no rooms are available, disable the "Book" button and provide a clear, user-friendly message explaining why booking isn't possible. This reduces unnecessary server requests and enhances user experience. |
| Server-side logic       | Enhance business logic to handle edge cases and failures gracefully. For instance, to address race conditions such as multiple users attempting to book the last available room, consider adding users to a waiting list or suggesting alternatives. |
| Asynchronous processing | Process commands asynchronously by placing them in a queue, instead of handling them synchronously. |

### Understand Queries

Queries **never alter data**. They return **Data Transfer Objects (DTOs)** that present required data in a convenient format, without any domain logic. This distinct separation simplifies system design and implementation.

---

## Separate Read Models and Write Models

Separating the read model from the write model simplifies system design by addressing specific concerns for **data writes** and **data reads**. This separation improves clarity, scalability, and performance but introduces trade-offs:

- **OR/M Limitations:** Object-Relational Mapping (O/RM) frameworks can't automatically generate CQRS code from a database schema, necessitating custom logic to bridge the gap.

### Solution

<p align="center">
<img src="/doc/CQRSDiagram.png" alt="Pets Store Arhictecture Diagram" width="860" height="650">
</p>
**Diagram that shows a CQRS architecture with separate read and write data stores.**

Assuming the there is a huge number of users > 10 concurrent users for the but there is only a few admin users. Isolation of the services and data improves the needs and effectiveness of the scalability. 
In this case, only admin or query services/data required to be scaled. System or secured data in the database also isolated from the public users.

#### Separate Models in Different Data Stores

A more advanced CQRS implementation uses **distinct data stores** for the read and write models. This allows you to scale each model independently and use different storage technologies. For example, you can use a **document database** for reads and a **relational database** for writes.

When using separate data stores, you must ensure synchronization between them. A common pattern is to have the write model **publish events** when updating the database, which the read model uses to refresh its data. For more information, see **Event-driven architecture**. Because message brokers and databases cannot typically participate in a single distributed transaction, **consistency challenges** may occur. For more details, see **Idempotent message processing**.

The read data store can have its own schema optimized for queries:

- Store materialized views to avoid complex joins or O/RM mappings.
- Use read-only replicas to improve latency and availability.
- Structure may differ from the write store to optimize query efficiency.

---

### Benefits of CQRS

- **Independent scaling:** Read and write models can scale separately, minimizing lock contention and improving performance under load.
- **Optimized schemas:** Read operations can use query-optimized schemas, and write operations can use update-optimized schemas.
- **Security:** Separating reads and writes ensures only authorized entities can perform write operations.
- **Separation of concerns:** Write side handles complex business logic; read side focuses on query efficiency.
- **Simpler queries:** Materialized views or read-optimized schemas reduce query complexity.

---

### Problems and Considerations

- **Increased complexity:** CQRS introduces design complexity, especially with Event Sourcing.
- **Messaging challenges:** Using messaging for commands and events requires handling failures, duplicates, and retries.
- **Eventual consistency:** Read data might not immediately reflect recent writes, leading to stale data. Handling stale data scenarios carefully is required.

---

### When to Use This Pattern

- Collaborative environments with multiple users accessing the same data.
- Task-based user interfaces with complex processes.
- Write model includes a full command-processing stack with business logic and validations.
- Read model provides DTOs without business logic, eventually consistent with writes.
- Performance tuning needed, especially when reads exceed writes.
- Teams with separate development concerns for read and write models.
- Evolving systems with frequent changes to business rules or model versions.
- Systems requiring integration with other subsystems or Event Sourcing patterns.

---

### When This Pattern Might Not Be Suitable

- Simple domains or business rules.
- Applications with basic CRUD-style user interfaces and data access operations.
