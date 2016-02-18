using RPSGJsonWCFService.Database.Base;
using RPSGJsonWCFService.Database.MySqlDataBase;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;

namespace RPSGJsonWCFService.WCFServiceInstance
{
    public class DBService
    {
        protected static string GetConnectionString()
        {
            return ConfigurationManager.ConnectionStrings["DatabaseConnecionString"].ConnectionString;
        }


        public BaseDatabaseWarpper CreateDatabaseWarpper()
        {
            return new MySqlDataBaseWarpper(GetConnectionString());
        }

    }
}