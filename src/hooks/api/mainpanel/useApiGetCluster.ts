import { api } from "../../../api/index";
import { keepPreviousData, useQuery } from "@tanstack/vue-query";
import { QUERY_KEY } from "../queryKey";

type clusterProps = {
    [index: string]: string[];
}

export const useApiGetCluster = () => {
    const getCluster = async () => {
        const res = await api.get(`/api/zkui/clusters`);
        return res.data as clusterProps;
    }

    return useQuery({
        queryKey: [QUERY_KEY.cluster],
        queryFn: () => getCluster(),
        placeholderData: keepPreviousData
    })
}
