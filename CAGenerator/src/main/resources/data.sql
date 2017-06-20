insert into USER(first_name, last_name, email, password, salt) values ("Pera", "Peric", "ppera@gmail.com", "H2I8mOzGhxXKVRgtLyDRvE7hKyqkSjeZBlx2rP6BQ0Q=", "qO7WuIQgH7tNT+/eEpJ23sOkbHH2rfocccNxCrpn8UM=")
insert into USER(first_name, last_name, email, password, salt) values ("Janko", "Jankovic", "jjanko@gmail.com", "gYxaSoG8bld7bEnwnAHrhZQXpv3yLV59oRiqVU0BaFs=", "prtDOK7ObPLI2addmMlIyYS8+z6V43FLr+oDK3aFIwo=")
insert into USER(first_name, last_name, email, password, salt) values ("Marko", "Markovic", "mmarko@gmail.com", "DoZv1FN6McykvGv8RJgYMyqqTint16gir1XIxKNWzQ4=", "LMtDnpvaxCr9fYkbFmqKyn/cAllaZkeuUKDPjympv9U=")
insert into USER(first_name, last_name, email, password, salt) values ("Jovan", "Jovanovic", "jjovan@gmail.com", "Qjmt21mSW6PfyhbM8iTE98L+83vzSLM77cWy9LDBVJE=", "4q7kBPh+PbRMjN/Dw62x2fpFhyXRssMdSTmI/BYZcAU=")

insert into ROLE(name) values ("SystemAdmin")
insert into ROLE(name) values ("CentralBankAdmin")
insert into ROLE(name) values ("Firma")

insert into PERMISSION(name) values ("GetAllUsers")
insert into PERMISSION(name) values ("GetUserByEmail")
insert into PERMISSION(name) values ("AddUser")
insert into PERMISSION(name) values ("UpdateUser")
insert into PERMISSION(name) values ("DeleteUser")

insert into PERMISSION(name) values ("GetAllRoles")
insert into PERMISSION(name) values ("GetRole")
insert into PERMISSION(name) values ("AddRole")
insert into PERMISSION(name) values ("UpdateRole")
insert into PERMISSION(name) values ("DeleteRole")

insert into PERMISSION(name) values ("GenCertificate")
insert into PERMISSION(name) values ("GetCertificate")
insert into PERMISSION(name) values ("ExportCertificate")

insert into PERMISSION(name) values ("GenKeyStore")
insert into PERMISSION(name) values ("GenCertificateRequest")

insert into USERS_ROLES(user_id, role_id) values (3, 1)
insert into USERS_ROLES(user_id, role_id) values (3, 2)
insert into USERS_ROLES(user_id, role_id) values (1, 3)
insert into USERS_ROLES(user_id, role_id) values (2, 3)
insert into USERS_ROLES(user_id, role_id) values (4, 2)

-- SYSTEM_ADMIN (USER, ROLE)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 1)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 2)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 3)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 4)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 5)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 6)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 7)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 8)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 9)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 10)

-- CENTRAL_BANK_ADMIN 
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 11)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 12)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 13)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 14)

-- FIRMA
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 12)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 13)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 15)

