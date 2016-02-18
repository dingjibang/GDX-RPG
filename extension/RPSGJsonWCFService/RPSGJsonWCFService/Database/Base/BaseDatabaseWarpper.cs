using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using System.Web;

namespace RPSGJsonWCFService.Database.Base
{
    public abstract class BaseDatabaseWarpper :IDisposable
    {

        private DbConnection m_Connection;

        public DbConnection Connection
        {
            get { return m_Connection; }
            set { m_Connection = value; }
        }
        
        protected BaseDatabaseWarpper()
        {
 
        }

        protected BaseDatabaseWarpper(string m_ConnectionString)
        {
            Connect(m_ConnectionString);
        }

        protected abstract DbDataAdapter CreateDataAdapter(string cmdString);

        protected abstract DbDataAdapter CreateDataAdapter(DbCommand cmdString);


        protected abstract DbCommand CreateCommand(string cmdString);

        protected abstract DbConnection CreateConnection(string m_ConnectionString);


        public abstract DbParameter GetParameter(string name, object Value);

        public DbCommand GetCommand(string cmdString)
        {
            return CreateCommand(cmdString);
        }

        public DbCommand GetCommand(string cmdString,DbTransaction trans)
        {
            var cmm = CreateCommand(cmdString);
            cmm.Transaction = trans;
            return cmm;
        }

        public void Connect(string m_ConnectionString)
        {
            if (m_Connection != null)
            {
                throw new AccessViolationException("Has Connected");
            }
            if (string.IsNullOrEmpty(m_ConnectionString))
            {
                throw new ArgumentNullException("m_ConnectionString is null  ");
            }
            m_Connection = CreateConnection(m_ConnectionString);
            m_Connection.Open();
        }



        public void Close()
        {
            if (m_Connection != null)
            {
                m_Connection.Close();
                m_Connection = null;
            }
        }

        public virtual void Dispose()
        {
            this.Close();
        }


        public static String GetSafeString(string str)
        {
            return '\'' + str + '\'';
        }

        #region DBAction

        public const string usernameMatch = @"^[\w\-]{4,20}$";
        public static string CreateCheckSameNameSql(string userName)
        {
            return string.Format("SELECT COUNT(*) FROM USER_TABLE WHERE USERID={0}", GetSafeString(userName));
        }

        public static string CreateCheckUSERIDSql(string userName)
        {
            return string.Format("SELECT FID FROM USER_TABLE WHERE USERID={0}", GetSafeString(userName));
        }

        public static void CheckLongDateTimeValue(string value)
        {
            DateTime.ParseExact(value, "yyyy-MM-dd HH:mm:ss", System.Globalization.DateTimeFormatInfo.InvariantInfo);
        }

        public static void CheckDoubleValue(string value)
        {
            Double.Parse(value);
        }

        public static void ChecklongValue(string value)
        {
            long.Parse(value);
        }
        public static void CheckIntValue(string value)
        {
            int.Parse(value);
        }

        public static void CheckBitValue(string value, string ColumnName)
        {
            if (value != "0" && value != "1")
            {
                throw new ArgumentException(string.Format("{0} is invalid,ColumnName={1}", value, ColumnName));
            }
        }
        public static void CheckBitNullableValue(string value, string ColumnName)
        {
            if (value != "0" && value != "1" && !string.IsNullOrEmpty(value))
            {
                throw new ArgumentException(string.Format("{0} is invalid,ColumnName={1}", value, ColumnName));
            }
        }

        public static void CheckParamter(string key, string value)
        {
            if (key == null)
            {
                throw new NullReferenceException("key is null");
            }
            switch (key.ToLower())
            {
                case "mailaddress":
                    {
                        if (value != null && value.Length > 360)
                        {
                            throw new ArgumentException("mailaddress is too long");
                        }
                    } break;
                case "nickname":
                    {
                        if (value != null && value.Length > 10)
                        {
                            throw new ArgumentException("nickname is too long");
                        }
                    } break;
                case "userid":
                case "username":
                    if (value == null)
                    {
                        throw new NullReferenceException("username is null");
                    }
                    if (value.Length > 20)
                    {
                        throw new ArgumentException("username is too long");
                    }
                    //if (!System.Text.RegularExpressions.Regex.IsMatch(key, usernameMatch))
                    //{
                    //    throw new ArgumentException("username Error chars");
                    //}
                    break;
                case "password":
                    if (value == null)
                    {
                        throw new NullReferenceException("password is null");
                    }
                    if (value.Length > 64)
                    {
                        throw new ArgumentException("password is too long");
                    } break;
                case "phonenumber":
                case "phonenum":
                    if (value != null && value.Length > 20)
                    {
                        throw new ArgumentException("phonenum is too long");
                    } break;

                default:
                    throw new ArgumentException(string.Format("{0} is invalid key", key));
            }
        }
        public bool ContainsUserNameWorkProc(String userName, DbTransaction trans)
        {
            try
            {
                CheckParamter("userName", userName);
            }
            catch (Exception ex)
            {
                LogUtil.Info(ex.Message);
                return false;
            }
            return CheckContainsCommand(CreateCheckSameNameSql(userName), trans);
        }

        public long GetUserFIDThrowException(String userName, DbTransaction trans)
        {
            long? l = GetUserFID(userName, trans);
            if (l == null)
            {
                throw new NotExistUserNameErrorException(string.Format("{0} doesn't exist", userName));
            }
            return (long)l;
        }
        public long? GetUserFID(String userName, DbTransaction trans)
        {
            try
            {
                CheckParamter("userName", userName);
            }
            catch (Exception ex)
            {
                LogUtil.Info(ex.Message);
                return -1;
            }
            return GetSinglelongresult(CreateCheckUSERIDSql(userName), trans);
        }

        public void UserNameCheck(string userID, DbTransaction trans)
        {
            if (!ContainsUserNameWorkProc(userID, trans))
            {
                throw new NotExistUserNameErrorException(string.Format("{0} doesn't exist", userID));
            }
        }

        public System.Data.DataTable GetSelectTableResult(string SqlText, DbTransaction trans)
        {
            using (var cmm = GetCommand(SqlText, trans))
            {
                using (var adp = this.CreateDataAdapter(cmm))
                {
                    System.Data.DataSet dataSet = new System.Data.DataSet();
                    try
                    {
                        adp.Fill(dataSet);
                    }
                    catch (Exception ex)
                    {
                        throw new Exception("ERRORSQL:" + cmm + "\r\n" + ex.Message, ex);
                    }
                    return dataSet.Tables[0];
                }
            }
        }
        public System.Data.DataSet GetSelectResult(string SqlText, DbTransaction trans)
        {
            using (var cmm = GetCommand(SqlText,  trans))
            {
                using (var adp =  CreateDataAdapter(cmm))
                {
                    System.Data.DataSet dataSet = new System.Data.DataSet();
                    try
                    {
                        adp.Fill(dataSet);
                    }
                    catch (Exception ex)
                    {
                        throw new Exception("ERRORSQL:" + cmm + "\r\n" + ex.Message, ex);
                    }
                    return dataSet;
                }
            }
        }

        public System.Data.DataSet GetSelectResult(DbCommand cmm, DbTransaction trans)
        {
            using (var adp = CreateDataAdapter(cmm))
            {
                System.Data.DataSet dataSet = new System.Data.DataSet();
                adp.Fill(dataSet);
                return dataSet;
            }
        }
        public long? GetSinglelongresult(string SqlText, DbTransaction trans)
        {
            using (var cmm = GetCommand(SqlText,  trans))
            {
                try
                {
                    return GetSinglelongresult(cmm,  trans);
                }
                catch (Exception ex)
                {

                    throw new Exception("ERRORSQL:" + SqlText + "\r\n" + ex.Message, ex);
                }

            }
        }
        public long? GetSinglelongresult(DbCommand cmm, DbTransaction trans)
        {
            System.Data.DataSet dataSet = GetSelectResult(cmm, trans);
            System.Data.DataTable tbl = dataSet.Tables[0];
            if (tbl.Rows.Count != 0)
            {
                if (tbl.Rows[0][0] == DBNull.Value)
                {
                    return null;
                }
                try
                {
                    return Convert.ToInt64(tbl.Rows[0][0]);
                }
                catch (Exception ex)
                {
#if DEBUG
                    LogUtil.Debug("tbl.Rows[0][0]=" + tbl.Rows[0][0].ToString());
                    LogUtil.Error(ex.Message, ex);
#endif
                    throw ex;
                }

            }
            else
            {
                return null;
            }
        }


        public bool CheckContainsCommand(string sqlTEXT, DbTransaction trans)
        {
            using (var cmm = GetCommand(sqlTEXT,  trans))
            {
                using (var adp = this.CreateDataAdapter(cmm))
                {
                    System.Data.DataSet ds = new System.Data.DataSet();
                    try
                    {
                        adp.Fill(ds);
                    }
                    catch (Exception ex)
                    {
                        throw new Exception("ERRORSQL:" + sqlTEXT + "\r\n" + ex.Message, ex);
                    }

                    if ((long)(ds.Tables[0].Rows[0][0]) != 0)
                    {
                        return true;
                    }
                }
            }

            return false;
        }

        public void ExcuteCommand(String CMM, DbTransaction trans)
        {
            try
            {
                using (var cmm = GetCommand(CMM,  trans))
                {
                    cmm.ExecuteNonQuery();
                }
            }
            catch (Exception ex)
            {

                throw new Exception("ERRORSQL:" + CMM + "\r\n" + ex.Message, ex);
            }

        }
        public void ExcuteCommand(String CMM, DbTransaction trans, object value)
        {
            try
            {
                using (var cmm = GetCommand(CMM,  trans))
                {
                    cmm.Parameters.Add(GetParameter("@VALUE", value));
                    cmm.ExecuteNonQuery();
                }
            }
            catch (Exception ex)
            {

                throw new Exception("ERRORSQL:" + CMM + "\r\nValue:" + Convert.ToString(value) + "\r\n" + ex.Message, ex);
            }

        }
        #endregion
    }


    public class NotExistUserNameErrorException : Exception
    {
        public NotExistUserNameErrorException()
            : base()
        {
        }
        public NotExistUserNameErrorException(string message)
            : base(message)
        {
        }
    }

    public class LogonPasswordErrorException : Exception
    {
        public LogonPasswordErrorException()
            : base()
        {
        }
        public LogonPasswordErrorException(string message)
            : base(message)
        {
        }
    }
}