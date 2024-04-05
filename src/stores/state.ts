import {defineStore} from "pinia";
import {ref} from "vue";

export const useGlobalState = defineStore('state', () => {
    const clusterData = ref<Array<string | object>>([]);
    const updateClusterData = (newData: string[] | object[]) => {
        console.log("this is state pinia", newData);
        clusterData.value = newData;
        console.log("this is updated pinia state", newData);
    }

    return { clusterData, updateClusterData };
});

export default useGlobalState;
