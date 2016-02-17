using RPSGJsonWCFService.Database.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RPSGJsonWCFService.WCFServiceInstance
{
    public class AddSaveFileService : DBService
    {
        public int AddSaveFile(string userid, string filename,string data)
        {
            using (var Warpper= CreateDatabaseWarpper())
            {
                var trans = Warpper.Connection.BeginTransaction();
                try
                {
                    if (!Warpper.ContainsUserNameWorkProc(userid, trans)) throw new NotExistUserNameErrorException(string.Format("username not existed", userid));
                    string basedir = System.AppDomain.CurrentDomain.BaseDirectory;
                    string appdir = System.IO.Path.Combine(basedir, "app_saves");
                    string usersavesdir = System.IO.Path.Combine(appdir, userid);
                    string filepath = System.IO.Path.Combine(usersavesdir,filename);
                    System.IO.File.WriteAllBytes(filepath, Convert.FromBase64String(data));
                    trans.Commit();
                    return 0;
                    //if (System.IO.File.Exists(filepath))
                    //{
                    //    byte[] bytes = System.IO.File.ReadAllBytes(filepath);
                    //    return Convert.ToBase64String(bytes);
                    //}
                    //if (!Warpper.CheckContainsCommand(CreateCheckLogonSql(userid, Password), trans)) throw new LogonPasswordErrorException(string.Format("Password is Wrong", userid));     
                }
                catch (Exception ex)
                {
                    LogUtil.Error(ex.Message, ex);
                    trans.Rollback();
                    throw ex;
                }
                
            }
            return -1;
           }
    }
}