CREATE VIEW "auth"."auth_view" AS
(
select "au"."id",
       "au"."password_hash",
       "au"."user_name",
       "au"."auth_role",
       "au"."phone_number",
       "au"."email",
       "ep"."employee_code",
       "ep"."employee_name",
       "ep"."employee_role",
       "epn"."position_code",
       "epn"."position_name",
       "ed"."department_code",
       "ed"."department_name",
       "et"."team_code",
       "et"."team_name"
from "auth"."account" "au"
         inner join "employee"."profile" "ep" on "au"."id" = "ep"."id"
         inner join "employee"."position" "epn" on "ep"."position_id" = "epn"."id"
         inner join "employee"."team" "et" on "ep"."team_id" = "et"."id"
         inner join "employee"."department" "ed" on "et"."department_id" = "ed"."id"
where "au"."auth_role" <> 'LEFT');