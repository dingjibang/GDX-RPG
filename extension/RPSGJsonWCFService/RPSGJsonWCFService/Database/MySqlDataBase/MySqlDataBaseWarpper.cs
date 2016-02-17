using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;

namespace RPSGJsonWCFService.Database.MySqlDataBase
{
    public class MySqlDataBaseWarpper:Base.BaseDatabaseWarpper
    {
        public MySqlDataBaseWarpper():base()
        { 

        }


        public MySqlDataBaseWarpper(string cnnString):base(cnnString)
        {
 
        }

        protected override System.Data.Common.DbDataAdapter CreateDataAdapter(string cmdString)
        {
            if (Connection == null)
            {
                return null;
            }

            if (string.IsNullOrEmpty(cmdString))
            {
                return null;
            }

            return new MySqlDataAdapter((MySqlCommand)CreateCommand(cmdString));
        }

        protected override System.Data.Common.DbDataAdapter CreateDataAdapter(System.Data.Common.DbCommand cmd)
        {
            return new MySqlDataAdapter((MySqlCommand)cmd);
        }

        protected override System.Data.Common.DbCommand CreateCommand(string cmdString)
        {
            return new MySqlCommand(cmdString, (MySqlConnection)Connection);
        }

        protected override System.Data.Common.DbConnection CreateConnection(string cnnString)
        {
            return new MySqlConnection(cnnString);
        }

        public override System.Data.Common.DbParameter GetParameter(string name, object Value)
        {
            return new MySqlParameter(name, Value);
        }
    }
}