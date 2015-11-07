using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class EnumDisplayNameAttribute : Attribute
    {

        // Summary:
        //     Initializes a new instance of the System.ComponentModel.DisplayNameAttribute
        //     class.
        public EnumDisplayNameAttribute()
        {

        }
        //
        // Summary:
        //     Initializes a new instance of the System.ComponentModel.DisplayNameAttribute
        //     class using the display name.
        //
        // Parameters:
        //   displayName:
        //     The display name.
        public EnumDisplayNameAttribute(string displayName)
        {
            DisplayNameValue = displayName;
        }

        // Summary:
        //     Gets the display name for a property, event, or public void method that takes
        //     no arguments stored in this attribute.
        //
        // Returns:
        //     The display name.
        public virtual string DisplayName { get { return DisplayNameValue; } }
        //
        // Summary:
        //     Gets or sets the display name.
        //
        // Returns:
        //     The display name.
        protected string DisplayNameValue { get; set; }

        // Summary:
        //     Determines whether two System.ComponentModel.DisplayNameAttribute instances
        //     are equal.
        //
        // Parameters:
        //   obj:
        //     The System.ComponentModel.DisplayNameAttribute to test the value equality
        //     of.
        //
        // Returns:
        //     true if the value of the given object is equal to that of the current object;
        //     otherwise, false.
        public override bool Equals(object obj)
        {
            if (obj is EnumDisplayNameAttribute)
            {
                return ((EnumDisplayNameAttribute)obj).DisplayNameValue == DisplayNameValue;
            }
            return false;
        }
        //
        // Summary:
        //     Returns the hash code for this instance.
        //
        // Returns:
        //     A hash code for the current System.ComponentModel.DisplayNameAttribute.
        public override int GetHashCode()
        {
            if (DisplayName == null)
            {
                return 0;
            }
            return DisplayName.GetHashCode();

        }
        //
        // Summary:
        //     Determines if this attribute is the default.
        //
        // Returns:
        //     true if the attribute is the default value for this attribute class; otherwise,
        //     false.
        public override bool IsDefaultAttribute()
        {
            return string.IsNullOrEmpty(DisplayName);
        }
    }
}
