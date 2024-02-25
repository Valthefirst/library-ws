insert into patrons(patron_id, first_name, last_name, email_address, contact_method_preference, street_address, city, province, country, postal_code)
values('e5913a79-9b1e-4516-9ffd-06578e7af261', 'Vilma', 'Chawner', 'vchawner0@phoca.cz', 'MAIL', '8452 Anhalt Park', 'Chambly', 'Québec', 'Canada', 'J3L 5Y6');

insert into patron_phonenumbers(patron_id, type, number) values(1, 'HOME', '515-555-5555');
insert into patron_phonenumbers(patron_id, type, number) values(1, 'MOBILE', '514-555-4444');
