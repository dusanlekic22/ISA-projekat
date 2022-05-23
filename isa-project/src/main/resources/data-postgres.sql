-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

-- username:admin password:123
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski', 'asd@example.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into admin (id, first_time_logged_in) values (1, true);

--insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski', 'asd@example.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('pape', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Aleksa', 'Papovic', 'asd@example.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_FISHING_TRAINER');
insert into role (name) values ('ROLE_CUSTOMER');
insert into role (name) values ('ROLE_COTTAGE_OWNER');

insert into app_user_roles (user_id, role_id) values (1, 1);
--insert into role (name) values ('ROLE_ADMIN');

insert into app_user_roles (user_id, role_id) values (2, 3);
insert into customer (loyality_program, points,id) values ('Basic',0,2);
 --insert into app_user_roles (user_id, role_id) values (1, 1);
 
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('mare', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'asd@example.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (3, 3);
insert into customer (id) values (3);


--insert into role (name) values ('ROLE_COTTAGE_OWNER');
--insert into app_user_roles (user_id, role_id) values (2, 2);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('jagodica', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Dusan', 'Lekic', 'asd@example.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (4, 4);
insert into cottage_owner (id) values (4);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('daco', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Danilo', 'Korac', 'dusanlekic2000@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (5, 3);
insert into customer (id) values (5);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('mare', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'asd@example.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (6, 3);
insert into customer (id) values (6);

insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into cottage (name, promo_description, bed_count, room_count, cottage_rules,address_id) values ('Vila Jezero', 'Najjaca', 3, 15,'Nema',1);
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Zrenjanin', 'Srbija', '24', '88','Strazilovska');
insert into cottage (name, promo_description, bed_count, room_count, cottage_rules,address_id,cottage_owner_id) values ('Turist', 'Najjaca', 3, 15,'Nema',2,4);
insert into cottage (name, promo_description, bed_count, room_count, cottage_rules,address_id,cottage_owner_id) values ('Garni Ideal', 'Najjaca', 3, 15,'Nema',3,4);
insert into cottage (name, promo_description, bed_count, room_count, cottage_rules,address_id,cottage_owner_id) values ('Royal ', 'Najjaca', 3, 15,'Nema',2,4);
insert into cottage (name, promo_description, bed_count, room_count, cottage_rules,address_id,cottage_owner_id) values ('Vila Jezero', 'Najjaca', 3, 15,'Nema',1,4);

insert into cottage_reservation (start_date, end_date, guest_capacity, price, confirmed, cottage_id,customer_id) values ('2022-06-15T13:22:11', '2022-06-18', 5, 70, true, 5,6);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, confirmed, cottage_id,customer_id) values ('2022-05-18', '2022-05-25', 5, 70, true,5,5);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, confirmed, cottage_id,customer_id) values ('2022-05-11', '2022-05-13', 6, 100, true,5,5);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-05-01', '2022-05-31',5);

insert into cottage_subscribers (cottage_id,customer_id) values (1,2)
