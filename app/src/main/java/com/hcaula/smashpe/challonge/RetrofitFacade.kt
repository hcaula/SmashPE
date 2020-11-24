package com.hcaula.smashpe.challonge

class RetrofitFacade {
    companion object {
        val retrofit = RetrofitInitializer().apiHelper()
    }
}