insert into USER(first_name, last_name, email, password) values ("Pera", "Peric", "ppera@gmail.com", "12345678")
insert into USER(first_name, last_name, email, password) values ("Janko", "Jankovic", "jjanko@gmail.com", "12345678")
insert into USER(first_name, last_name, email, password) values ("Marko", "Markovic", "mmarko@gmail.com", "12345678")
insert into USER(first_name, last_name, email, password) values ("Jovan", "Jovanovic", "jjovan@gmail.com", "12345678")

insert into ROLE(name) values ("Admin")
insert into ROLE(name) values ("Bank Manager")
insert into ROLE(name) values ("Client Manager")

insert into PERMISSION(name) values ("AddBank")
insert into PERMISSION(name) values ("UpdateBank")
insert into PERMISSION(name) values ("DeleteBank")

insert into PERMISSION(name) values ("AddBankAccount")
insert into PERMISSION(name) values ("UpdateBankAccount")
insert into PERMISSION(name) values ("DeleteBankAccount")

insert into PERMISSION(name) values ("AddBankMessage")
insert into PERMISSION(name) values ("UpdateBankMessage")
insert into PERMISSION(name) values ("DeleteBankMessage")

insert into PERMISSION(name) values ("AddCurrency")
insert into PERMISSION(name) values ("UpdateCurrency")
insert into PERMISSION(name) values ("DeleteCurrency")

insert into PERMISSION(name) values ("AddWorkType")
insert into PERMISSION(name) values ("UpdateWorkType")
insert into PERMISSION(name) values ("DeleteWorkType")

insert into PERMISSION(name) values ("AddClient")
insert into PERMISSION(name) values ("UpdateClient")
insert into PERMISSION(name) values ("DeleteClient")

insert into USERS_ROLES(user_id, role_id) values (3, 1)
insert into USERS_ROLES(user_id, role_id) values (1, 2)
insert into USERS_ROLES(user_id, role_id) values (2, 3)
insert into USERS_ROLES(user_id, role_id) values (3, 2)
insert into USERS_ROLES(user_id, role_id) values (4, 3)

insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 1)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 2)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 3)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 4)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 5)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 6)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 4)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 5)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 7)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 8)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 9)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 10)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 11)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 12)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 13)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 14)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 15)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 16)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 17)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 18)



-- neznam kako da proradi :/ INSERT INTO WORK_TYPES (code, name) VALUES (111, 'WT 1')


--INSERT INTO CLIENT_DETAILS (`client_type`, `address`, `email`, `first_name`, `jmbg`, `last_name`, `phone_number`, `fax`, `pib`, `web_page`, `work_type_id`) VALUES ('LEGAL_PERSON', '123', '123', '123', '132', 'asd', '54', '43', '54', '23', '1');

