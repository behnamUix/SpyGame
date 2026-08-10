package com.behnamuix.spygame.feature.configgame.domain.model

import com.behnamuix.spygame.utils.generateMd5Code

object  Agent{
    var count:Int=1
    var code:String= generateMd5Code(count)
}
