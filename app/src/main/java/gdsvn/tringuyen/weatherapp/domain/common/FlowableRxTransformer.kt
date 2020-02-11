package gdsvn.tringuyen.weatherapp.domain.common

import io.reactivex.FlowableTransformer

abstract class FlowableRxTransformer<T>: FlowableTransformer<T,T>