using RPSGJsonWCFService.Database.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RPSGJsonWCFService.WCFServiceInstance
{
    public class GetSaveFilesService : DBService
    {
        public string[] GetSaveFiles(string userid)
        {
            //using (var Warpper= CreateDatabaseWarpper())
            //{
                //var trans = Warpper.Connection.BeginTransaction();
                try
                {
                    //if (!Warpper.ContainsUserNameWorkProc(userid, trans)) throw new NotExistUserNameErrorException(string.Format("username not existed", userid));
                    string basedir = System.AppDomain.CurrentDomain.BaseDirectory;
                    string appdir = System.IO.Path.Combine(basedir, "app_saves");
                    string usersavesdir = System.IO.Path.Combine(appdir, userid);
                    if (System.IO.Directory.Exists(usersavesdir))
                    {
                        return System.IO.Directory.GetFiles(usersavesdir).Select(str => System.IO.Path.GetFileName(str)).ToArray();
                    }
                    //if (!Warpper.CheckContainsCommand(CreateCheckLogonSql(userid, Password), trans)) throw new LogonPasswordErrorException(string.Format("Password is Wrong", userid));     
                }
                catch (Exception ex)
                {
                    LogUtil.Error(ex.Message, ex);
                    //trans.Rollback();
                    throw ex;
                }
                //trans.Commit();
            //}
            return new string[] { };
           }
    }
}