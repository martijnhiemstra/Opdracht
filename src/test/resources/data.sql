INSERT INTO department (id, name) VALUES (1, 'Sales');
INSERT INTO department (id, name) VALUES (2, 'Marketing');
INSERT INTO department (id, name) VALUES (3, 'Engineering');
INSERT INTO department (id, name) VALUES (4, 'HR');
INSERT INTO department (id, name) VALUES (5, 'Finance');

INSERT INTO person (id, name, salary, status, start_date, department_id) VALUES
(1, 'John Doe', 5000.50, 'ACTIVE', '2023-01-15T08:30:00', 1),
(2, 'Jane Smith', 6500.75, 'ACTIVE', '2020-03-22T09:00:00', 2),
(3, 'Alice Johnson', 7000.25, 'ACTIVE', '2018-07-30T10:00:00', 3),
(4, 'Bob Brown', 5500.30, 'ON_LEAVE', '2022-11-01T11:00:00', 4),
(5, 'Charlie Williams', 4800.45, 'ON_LEAVE', '2019-05-10T12:00:00', 5),
(6, 'Diana Miller', 7200.90, 'ON_LEAVE', '2021-08-14T13:00:00', 1),
(7, 'Edward Wilson', 5100.60, 'TERMINATED', '2017-04-25T14:00:00', 2),
(8, 'Fiona Moore', 6000.80, 'TERMINATED', '2020-02-19T15:00:00', 3),
(9, 'George Taylor', 7500.95, 'TERMINATED', '2022-06-06T16:00:00', 4),
(10, 'Hannah Harris', 5300.50, 'TERMINATED', '2023-12-10T17:00:00', 5);
