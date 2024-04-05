import {defineStore} from "pinia";
import {ref} from "vue";
export const useToastStore = defineStore('toast', () => {
    const toastState = ref(false);
    const toastMessage = ref('');

    const openToast = (state: boolean) => {
        if (state) {
            toastMessage.value = "Success";
        } else {
            toastMessage.value = "Fail";
        }

        toastState.value = true;

        setTimeout(() => {
            toastState.value = false;
        }, 2000);
    }

    return { toastState, toastMessage, openToast }
})

export default useToastStore;
