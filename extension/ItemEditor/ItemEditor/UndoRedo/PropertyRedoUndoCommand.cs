using System;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class PropertyRedoUndoCommand : RedoUndoCommandBase
    {
        public PropertyRedoUndoCommand(object Target, string PropertyName, object OldValue, object NewValue)
        {
            m_Target = Target;
            this.PropertyName = PropertyName;
            m_NewValue = NewValue;
            m_OldValue = OldValue;
        }
        public override bool CanInvoke
        {
            get
            {
                return true;
            }
        }
        private object m_Target;
        public object Target
        {
            get { return m_Target; }
            set { m_Target = value; }
        }

        private object m_NewValue;

        public object NewValue
        {
            get { return m_NewValue; }
            set { m_NewValue = value; }
        }

        private object m_OldValue;

        public object OldValue
        {
            get { return m_OldValue; }
            set { m_OldValue = value; }
        }

        public string PropertyName
        {
            get;
            set;
        }

        public override void RedoInvoke()
        {
            if (m_Target == null)
            {
                return;
            }
            if (m_Target is ViewModelBase)
            {
                ((ViewModelBase)(m_Target)).BeginUndoRedo();
            }
            m_Target.SetPropertyValue(PropertyName, m_NewValue);
            if (m_Target is ViewModelBase)
            {
                ((ViewModelBase)(m_Target)).EndUndoRedo();
            }
        }
        public override void UndoInvoke()
        {
            if (m_Target == null)
            {
                return;
            }
            if (m_Target is ViewModelBase)
            {
                ((ViewModelBase)(m_Target)).BeginUndoRedo();
            }
            m_Target.SetPropertyValue(PropertyName, m_OldValue);
            if (m_Target is ViewModelBase)
            {
                ((ViewModelBase)(m_Target)).EndUndoRedo();
            }
        }
    }

    public static class PropertyExtFunction
    {
        private static System.Collections.Generic.Dictionary<string, ParameterExpression> gstPropertyCache = new System.Collections.Generic.Dictionary<string,ParameterExpression>();
        private static System.Collections.Generic.Dictionary<string, System.Reflection.PropertyInfo> gstPropertyInfoCache = new System.Collections.Generic.Dictionary<string, System.Reflection.PropertyInfo>();

        public static object GetPropertyValue(this object obj, string PropName)
        {
            if (obj == null || string.IsNullOrEmpty(PropName))
            {
                return null;
            }
            try
            {
                System.Reflection.PropertyInfo propInfo = GetPropInfo(obj, PropName);
                if (propInfo == null)
                {
                    return null;
                }
                return propInfo.GetValue(obj);
            }
            catch (Exception)
            {

            }
            return null;

        }

        public static void SetPropertyValue(this object obj,string PropName,object Value)
        {
            if (obj == null || string.IsNullOrEmpty(PropName))
            {
                return;
            }

            try
            {
                System.Reflection.PropertyInfo propInfo = GetPropInfo(obj, PropName);
                if (propInfo == null)
                {
                    return;
                }
                propInfo.SetValue(obj, Value);
            }
            catch (Exception)
            {
                
            }
        }

        private static System.Reflection.PropertyInfo GetPropInfo(object obj, string PropName)
        {
            var typ = obj.GetType();
            var typefullname = typ.FullName;
            var key = string.Format("{0} {1}", typefullname, PropName);
            System.Reflection.PropertyInfo propInfo = null;
            if (gstPropertyInfoCache.ContainsKey(key))
            {
                propInfo = gstPropertyInfoCache[key];
            }
            else
            {
                ParameterExpression parameter = null;

                if (gstPropertyCache.ContainsKey(typefullname))
                {
                    parameter = gstPropertyCache[typefullname];
                }
                else
                {
                    parameter = System.Linq.Expressions.Expression.Parameter(typ);
                    gstPropertyCache.Add(typefullname, parameter);
                }

                var property = System.Linq.Expressions.Expression.Property(parameter, PropName);
                if (property == null)
                {
                    return null;
                }

                //var setMethod = System.Linq.Expressions.Expression.Lambda(property, parameter);
                //Type typ = obj.GetType();
                propInfo = property.Member as System.Reflection.PropertyInfo;
                gstPropertyInfoCache.Add(key, propInfo);
            }

            return propInfo;
        }
    }
}
