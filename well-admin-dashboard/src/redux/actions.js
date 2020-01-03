export const SHOW_MODAL = 'SHOW_MODAL'
export const HIDE_MODAL = 'HIDE_MODAL'

export const showModal = (measurementPoint) => ({
    type: SHOW_MODAL,
    payload: measurementPoint
})