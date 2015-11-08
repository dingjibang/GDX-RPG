using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public enum ItemType
    {
        [EnumDisplayName("道具")]
        Item,
        [EnumDisplayName("装备")]
        Equipment,
        [EnumDisplayName("任务道具")]
        Task,
        [EnumDisplayName("符卡")]
        Spellcard,
        [EnumDisplayName("食物")]
        Cooking,
        [EnumDisplayName("素材")]
        Material
    }
}
