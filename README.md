# itprom-testjob
Test task for itprom.org company
## Task requirements
Задача
Разработать web-приложение, содержащее три справочника: профессии, отделы, сотрудники.
Каждый справочник должен содержать операции создания, редактирования, удаления соответствующей сущности.
Описание сущностей
1.	Профессия содержит поля Наименование, Примечание;
2.	Отдел содержит поля Наименование, Примечание, Родительский отдел. Т.е. у отделов может быть иерархия.
3.	Сотрудники содержит поля ФИО, Профессия, Отдел, Примечание
      Используемые технологии
1.	Java 21 + Spring Boot 3;
2.	JavaScript (TypeScript) + VueJs 3 (дизайн не имеет первостепенного значения);
3.	БД H2 embedded;
      Проект разместить на GitHub или Bitbucket.


## Start application
 
- use [run-backend.bat](run-backend.bat) to tun spring boot backend
- use [run-frontend.bat](run-backend.bat) to tun vue frontend


# About implementation
Currently, there are no tests in both applications

There is no possibility to change objects using frontend as well. 
You can use api requests to edit or delete any item (use postman collection:
[TestEndpoints.postman_collection.json](data%2Fpostman%2FTestEndpoints.postman_collection.json)).