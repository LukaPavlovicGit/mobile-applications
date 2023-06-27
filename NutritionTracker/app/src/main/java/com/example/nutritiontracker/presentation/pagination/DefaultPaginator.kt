package com.example.nutritiontracker.presentation.pagination



class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Unit,
    private inline val getPreviousKey: suspend () -> Key,
    private inline val getNextKey: suspend () -> Key,
    private inline val onError: suspend(Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
): Paginator<Key, Item> {


    private var currentKey : Key = initialKey
    private var isMakingRequest  = false

    override suspend fun loadNextItems() {
        if(isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        onRequest(currentKey)
        currentKey = getNextKey()
        onLoadUpdated(false)
    }

    override suspend fun loadPreviousItems() {
        if(isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        currentKey = getPreviousKey()
        onRequest(currentKey)
        onLoadUpdated(false)
    }

    override suspend  fun reset() {
        currentKey = initialKey
    }

}