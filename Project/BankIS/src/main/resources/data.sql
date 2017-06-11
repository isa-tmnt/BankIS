INSERT INTO bank(bank_code, billing_account, name, swift_code) VALUES (432, "123gh", "fddhg", "safasf")
INSERT INTO bank(bank_code, billing_account, name, swift_code) VALUES (321, "321hg", "aaaasd", "sdfghf")

insert into USER(first_name, last_name, email, password, salt, bank_id) values ("Pera", "Peric", "ppera@gmail.com", "H2I8mOzGhxXKVRgtLyDRvE7hKyqkSjeZBlx2rP6BQ0Q=", "qO7WuIQgH7tNT+/eEpJ23sOkbHH2rfocccNxCrpn8UM=", 1)
insert into USER(first_name, last_name, email, password, salt, bank_id) values ("Janko", "Jankovic", "jjanko@gmail.com", "gYxaSoG8bld7bEnwnAHrhZQXpv3yLV59oRiqVU0BaFs=", "prtDOK7ObPLI2addmMlIyYS8+z6V43FLr+oDK3aFIwo=", 1)
insert into USER(first_name, last_name, email, password, salt, bank_id) values ("Marko", "Markovic", "mmarko@gmail.com", "DoZv1FN6McykvGv8RJgYMyqqTint16gir1XIxKNWzQ4=", "LMtDnpvaxCr9fYkbFmqKyn/cAllaZkeuUKDPjympv9U=", 1)
insert into USER(first_name, last_name, email, password, salt, bank_id) values ("Jovan", "Jovanovic", "jjovan@gmail.com", "Qjmt21mSW6PfyhbM8iTE98L+83vzSLM77cWy9LDBVJE=", "4q7kBPh+PbRMjN/Dw62x2fpFhyXRssMdSTmI/BYZcAU=", 1)

insert into ROLE(name) values ("Admin")
insert into ROLE(name) values ("Bank Manager")
insert into ROLE(name) values ("Client Manager")

insert into PERMISSION(name) values ("GetAllBanks")
insert into PERMISSION(name) values ("GetBank")
insert into PERMISSION(name) values ("AddBank")
insert into PERMISSION(name) values ("UpdateBank")
insert into PERMISSION(name) values ("DeleteBank")

insert into PERMISSION(name) values ("GetAllBankAccounts")
insert into PERMISSION(name) values ("GetBankAccount")
insert into PERMISSION(name) values ("AddBankAccount")
insert into PERMISSION(name) values ("UpdateBankAccount")
insert into PERMISSION(name) values ("DeleteBankAccount")

insert into PERMISSION(name) values ("GetAllBankMessages")
insert into PERMISSION(name) values ("GetBankMessage")
insert into PERMISSION(name) values ("AddBankMessage")
insert into PERMISSION(name) values ("UpdateBankMessage")
insert into PERMISSION(name) values ("DeleteBankMessage")

insert into PERMISSION(name) values ("GetAllCurrencies")
insert into PERMISSION(name) values ("GetCurrency")
insert into PERMISSION(name) values ("AddCurrency")
insert into PERMISSION(name) values ("UpdateCurrency")
insert into PERMISSION(name) values ("DeleteCurrency")

insert into PERMISSION(name) values ("GetAllWorkTypes")
insert into PERMISSION(name) values ("GetWorkType")
insert into PERMISSION(name) values ("AddWorkType")
insert into PERMISSION(name) values ("UpdateWorkType")
insert into PERMISSION(name) values ("DeleteWorkType")

insert into PERMISSION(name) values ("GetAllClients")
insert into PERMISSION(name) values ("GetClient")
insert into PERMISSION(name) values ("AddClient")
insert into PERMISSION(name) values ("UpdateClient")
insert into PERMISSION(name) values ("DeleteClient")

insert into PERMISSION(name) values ("GetAllUsers")
insert into PERMISSION(name) values ("GetUserByEmail")
insert into PERMISSION(name) values ("AddUser")
insert into PERMISSION(name) values ("UpdateUser")
insert into PERMISSION(name) values ("DeleteUser")

insert into USERS_ROLES(user_id, role_id) values (3, 1)
insert into USERS_ROLES(user_id, role_id) values (1, 2)
insert into USERS_ROLES(user_id, role_id) values (2, 3)
insert into USERS_ROLES(user_id, role_id) values (3, 2)
insert into USERS_ROLES(user_id, role_id) values (4, 3)

-- ADMIN (BANK, WORKTYPE, USER)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 1)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 2)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 3)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 4)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 5)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 21)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 22)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 23)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 24)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 25)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 31)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 32)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 33)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 34)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (1, 35)

-- BANK MANAGER (BANK_ACCOUNT, BANK_MESSAGES, CURRENCY, CLIENT, WORKTYPE(GET))
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 6)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 7)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 8)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 9)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 10)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 11)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 12)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 13)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 14)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 15)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 16)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 17)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 18)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 19)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 20)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 26)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 27)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 28)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 29)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 30)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 21)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (2, 22)

-- CLIENT MANAGER (BANK_ACCOUNT, CLIENT, CURRENCY(GET), WORKTYPE(GET), BANK_MESSAGES(GET))
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 6)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 7)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 8)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 9)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 10)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 26)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 27)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 28)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 29)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 30)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 21)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 22)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 16)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 17)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 11)
insert into ROLES_PERMISSIONS(role_id, permission_id) values (3, 12)

INSERT INTO currency(`currency_code`, `name`) VALUES ('123', 'EURO')
INSERT INTO currency(`currency_code`, `name`) VALUES ('123', 'DOLLAR')


INSERT INTO `bisdb`.`work_type` (`code`, `name`) VALUES ( '213', 'sdfsadf')


INSERT INTO `bisdb`.`client_details` (`client_type`, `address`, `email`, `first_name`, `jmbg`, `last_name`, `phone_number`, `fax`, `pib`, `web_page`, `work_type_id`) VALUES ('LEGAL_PERSON', 'sdfa', 'sadf', 'sf', '545', 'asdf', '654', '56', '54', '543', '1')


INSERT INTO `bisdb`.`currency` (`currency_code`, `name`) VALUES ('432', 'fdgdg')


INSERT INTO `bisdb`.`bank_account` (`account_number`, `end_date`, `start_date`, `status`, `bank_id`, `client_id`, `currency_id`) VALUES ('43243', '2000-08-08', '2000-08-08', '1', '1', '1', '1')


INSERT INTO `bisdb`.`daily_account_balance` (`id`, `amount_charged`, `amount_in_favor`, `date`, `new_state`, `previous_state`, `account_id`) VALUES ('1', '435', '543', '2010-10-10', '5465', '543', '1')


INSERT INTO `bisdb`.`bank_order` (`amount`, `bank_order_date`, `currency_date`, `debtor`, `direction`, `first_account`, `first_model`, `first_number`, `order_date`, `purpose_of_payment`, `recipient`, `second_account`, `second_model`, `second_number`, `urgently`, `daily_account_balance_id`) VALUES ('123', '2000-10-10', '2000-10-10', 'gsdfg', 'A', 'dsf', 'as', '432', '2000-10-10', 'sgdfgsdg', 'dgsdg', 'dfg', 'gf', '123', b'00000000', '1')
INSERT INTO `bisdb`.`bank_order` (`amount`, `bank_order_date`, `currency_date`, `debtor`, `direction`, `first_account`, `first_model`, `first_number`, `order_date`, `purpose_of_payment`, `recipient`, `second_account`, `second_model`, `second_number`, `urgently`, `daily_account_balance_id`) VALUES ('655', '2000-10-10', '2000-10-10', 'gsdfg', 'A', 'dsf', 'as', '432', '2000-10-10', 'sgdfgsdg', 'dgsdg', 'dfg', 'gf', '123', b'00000000', '1')
INSERT INTO `bisdb`.`bank_order` (`amount`, `bank_order_date`, `currency_date`, `debtor`, `direction`, `first_account`, `first_model`, `first_number`, `order_date`, `purpose_of_payment`, `recipient`, `second_account`, `second_model`, `second_number`, `urgently`, `daily_account_balance_id`) VALUES ('879', '2000-10-10', '2000-10-10', 'gsdfg', 'A', 'dsf', 'as', '432', '2000-10-10', 'sgdfgsdg', 'dgsdg', 'dfg', 'gf', '123', b'00000000', '1')

INSERT INTO `bisdb`.`bank_messages` (`description`, `message_code`) VALUES ('fdg', '123')


INSERT INTO `bisdb`.`interbank_transfer` (`amount`, `transfer_date`, `bank_message_id`, `recipient_bank_id`, `sender_bank_id`) VALUES ('700', '2010-10-10', '1', '1', '2')


INSERT INTO `bisdb`.`transfer_item` (`bank_order_id`, `transfer_id`) VALUES ('1', '1')
INSERT INTO `bisdb`.`transfer_item` (`bank_order_id`, `transfer_id`) VALUES ('2', '1')


