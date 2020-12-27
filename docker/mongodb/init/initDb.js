var admin = db.getSiblingDB("admin");
var hairSalonDb = db.getSiblingDB("hair-salon-db");

admin.createUser(
    {
        user: "root",
        pwd: "root",
        passwordDigestor: "server",
        roles: [
            {role: "root", db: "admin"}
        ]
    }
);

hairSalonDb.createUser(
    {
        user: "crm_user",
        pwd: "crm_user",
        passwordDigestor: "server",
        roles: [
            {role: "readWrite", db: "hair-salon-db"},
        ]
    }
);
