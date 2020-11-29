package com.hcaula.smashpe.challonge

import android.content.Context

class RetrofitFacade {
    companion object {
        fun retrofit(context: Context) = RetrofitInitializer(context).apiHelper()
    }
}