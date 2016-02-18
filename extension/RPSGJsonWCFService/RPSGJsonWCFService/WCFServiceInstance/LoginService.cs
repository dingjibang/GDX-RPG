using RPSGJsonWCFService.Database.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RPSGJsonWCFService.WCFServiceInstance
{
    public class LoginService:DBService
    {
        public void Login(string userid, string Password)
        {
            using (var Warpper= CreateDatabaseWarpper())
            {
                var trans = Warpper.Connection.BeginTransaction();
                try
                {
                    if (!Warpper.ContainsUserNameWorkProc(userid, trans)) throw new NotExistUserNameErrorException(string.Format("username not existed", userid));
                    if (!Warpper.CheckContainsCommand(CreateCheckLogonSql(userid, RegisterService.getSafePassword(Password)), trans)) throw new LogonPasswordErrorException(string.Format("Password is Wrong", userid));     
                }
                catch (Exception ex)
                {
                    LogUtil.Error(ex.Message, ex);
                    trans.Rollback();
                    throw ex;
                }
                trans.Commit();
            }
           }
        public static string CreateCheckLogonSql(string userName,string Password)
        {
            return string.Format("SELECT COUNT(*) FROM USER_TABLE WHERE USERID={0} AND PASSWORD={1}", BaseDatabaseWarpper.GetSafeString(userName), BaseDatabaseWarpper.GetSafeString(Password));
        }
    }
}