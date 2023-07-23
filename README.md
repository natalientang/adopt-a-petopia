# Adopt-A-Petopia

### With the increasing importance of pet adoption and finding loving homes for animals in need, this project aims to connect loving homes with adorable and deserving pets in need of adoption. Whether you're looking to adopt a furry friend or find a new family for a rescued animal, Adopt-A-Petopia is a web-based application to encourage pet adoption and facilitate the adoption process to make it easy and enjoyable.

### Pet Information Management:
* System must store information about each pet available for adoption.
* Each pet will have a unique ID, species, description, and be associated with a specific shelter.
* Species field should be a foreign key, linked to the species table.

### Adoption Recording:
* System must allow recording of pet adoptions by adopters.
* Each adoption record will have a unique ID, adoption date, and be associated with a specific pet and adopter.
* Pets and adopters should be foreign keys, linked to the pet and adopter tables.

### Adopter Information Management:
* System must store information about adopters who are interested in adopting pets.
* Each adopter will have a unique ID, name, email, phone number, and address.

### Pet Breeds and Species:
* System must support the classification of pets into breeds and species.
petBreed table wil be used to map pets to their respective breeds, allowing one pet to have multiple breeds, if applicable.
* Breed table will store information about each breed, including a unique ID, name, and description.
* Species table will store information about each species, including a unique ID, name, and description.

### Shelter Information:
* System must store information about shelters where pets are available for adoption.
* Each shelter will have a unique ID, name, address, and phone number.
