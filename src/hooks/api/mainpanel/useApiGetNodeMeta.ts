import { api } from "../../../api/index";
import { useQuery } from "@tanstack/vue-query";

type clusterNodeProps = {
  [index: string]: string[];
};
export const useApiGetNodeMeta = () => {
  const getMetaData = async (
    clusterName: string,
    address: string,
    meta: boolean = false,
  ) => {
    const res = await api.get(
      `/api/zkui/clusters/${clusterName}/node?path=${address}&meta=${meta}`,
    );
    return res.data as clusterNodeProps;
  };

  return (clusterName: string, address: string, meta: boolean) =>
    useQuery({
      queryKey: [clusterName, address, meta],
      queryFn: () => getMetaData(clusterName, address, meta),
    });
};
