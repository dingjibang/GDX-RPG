using RPSGJsonWCFService.Database.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RPSGJsonWCFService.WCFServiceInstance
{
    public class RegisterService : DBService
    {


        public static string CreateRegisterUserInsertSql(string userName, string Password)
        {
            BaseDatabaseWarpper.CheckParamter("userName", userName);
            BaseDatabaseWarpper.CheckParamter("Password", Password);

            return string.Format("INSERT INTO USER_TABLE (USERID,PASSWORD) VALUES ({0},{1})", BaseDatabaseWarpper.GetSafeString(userName), BaseDatabaseWarpper.GetSafeString(getSafePassword(Password)));
        }
        public int Register(string UserName, string Password)
        {

            BaseDatabaseWarpper.CheckParamter("userName", UserName);
            BaseDatabaseWarpper.CheckParamter("password", Password);
            using (var Warpper = this.CreateDatabaseWarpper())
            {
                var trans = Warpper.Connection.BeginTransaction();
                try
                {
                    if (Warpper.ContainsUserNameWorkProc(UserName, trans))
                    {
                        throw new RegisterUserSameNameException();
                    }

                    Warpper.ExcuteCommand(CreateRegisterUserInsertSql(UserName, Password),trans);
                }
                catch (RegisterUserSameNameException ex)
                {
                    trans.Rollback();
                    LogUtil.Error(ex.Message, ex);
                    throw ex;
                }
                catch (Exception ex)
                {
                    trans.Rollback();
                    LogUtil.Error(ex.Message, ex);
                    throw ex;
                }
                trans.Commit();
                return 0;
            }
        }


        public static string getSafePassword(string Password)
        {
           
            var  hashmd5 = new System.Security.Cryptography.MD5CryptoServiceProvider();
            var hashcode = hashmd5.ComputeHash(System.Text.Encoding.Default.GetBytes(Convert.ToBase64String(System.Text.UTF8Encoding.UTF8.GetBytes(Password))));
            return BitConverter.ToString(hashcode);
        }
    }

    public class RegisterUserSameNameException : Exception
    {
        public RegisterUserSameNameException()
            : base()
        {
        }
        public RegisterUserSameNameException(string message)
            : base(message)
        {
        }
    }
}