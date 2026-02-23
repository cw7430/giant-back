CREATE VIEW "employee"."employee_profile_view" AS
(
select "ep"."id",
       "ep"."employee_code",
       "ep"."employee_name",
       "ep"."employee_role",
       "epn"."position_code",
       "epn"."position_name",
       "ed"."department_code",
       "ed"."department_name",
       "et"."team_code",
       "et"."team_name",
       "au"."phone_number",
       "au"."email",
       "epc"."employee_name" as "created_employee_name",
       "epu"."employee_name" as "updated_employee_name",
       "ep"."created_at",
       "ep"."updated_at",
       "ep"."left_at"
from "employee"."profile" "ep"
         inner join "auth"."account" "au"
                    on "au"."id" = "ep"."id"
         left join "employee"."position" "epn"
                   on "epn"."id" = "ep"."position_id"
         left join "employee"."team" "et"
                   on "et"."id" = "ep"."team_id"
         left join "employee"."department" "ed"
                   on "ed"."id" = "et"."department_id"
         left join "employee"."profile" "epc"
                   on "epc"."id" = "ep"."created_by"
         left join "employee"."profile" "epu"
                   on "epu"."id" = "ep"."updated_by"
    );