using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public enum deadableType
    {
        [EnumDisplayName("只能给死人使用的")]
        yes,
        [EnumDisplayName("只能给活人使用的")]
        no,
        [EnumDisplayName("可以给所有人使用的")]
        all,
    }
}
