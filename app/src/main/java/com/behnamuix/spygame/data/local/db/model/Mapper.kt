package com.behnamuix.spygame.data.local.db.model

fun KeywordEntity.toKeyword(): KeyWord {
    return KeyWord(
        id = id,
        word = word
    )
}

fun KeyWord.toKeywordEntity(): KeywordEntity {
    return KeywordEntity(
        id = id,
        word = word
    )
}


