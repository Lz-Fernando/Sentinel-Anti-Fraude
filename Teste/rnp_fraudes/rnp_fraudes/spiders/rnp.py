import scrapy


class RnpSpider(scrapy.Spider):
    name = "rnp_spider"
    allowed_domains = ["catalogodefraudes.rnp.br"]
    start_urls = ["https://catalogodefraudes.rnp.br/"]

    def parse(self, response):
        # Captura todos os links das fraudes
        fraudes_links = response.css("div.name h3 a::attr(href)").getall()

        # Segue cada link para extrair detalhes
        for link in fraudes_links:
            yield response.follow(link, callback=self.parse_fraude)

        # Captura o link da próxima página e segue
        next_page = response.css("ul.pagination li.next a::attr(href)").get()
        if next_page:
            yield response.follow(next_page, callback=self.parse)

    def parse_fraude(self, response):
        # Extrai detalhes da fraude
        titulo = response.css("h2::text").get()
        assunto = response.css("h4::text").get()
        descricao = " ".join(response.css("pre::text").getall()).strip()

        yield {
            "titulo": titulo,
            "assunto": assunto,
            "descricao": descricao,
            "url": response.url
        }