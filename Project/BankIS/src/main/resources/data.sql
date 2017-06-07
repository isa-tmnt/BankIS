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



INSERT INTO bank(`bank_code`, `billing_account`, `name`, `swift_code`) VALUES (432, 442, 'fdd', 'ghfgf')
INSERT INTO `bisdb`.`bank` (`bank_code`, `billing_account`, `name`, `swift_code`) VALUES ('321', '543', 'jhg', 'jghgh')


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


