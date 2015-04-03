import java.io.{File}
import scalaxb.compiler.Config


object BannoChunkSizeTest extends TestBase {
  val DEFAULT_SEQ_CHUNK_SIZE = 10 // This is the default inside scalaxb - here just for clarity

  def generate(inFile:File, sequenceChunkSize:Int = DEFAULT_SEQ_CHUNK_SIZE, wrappedTypes:List[String] = Nil) = module.process(inFile,
    Config(packageNames = Map(None -> Some("big") ),
      outdir = tmp,
      classPrefix = Some("X"),
      paramPrefix = None,
           wrappedComplexTypes = wrappedTypes,
           contentsSizeLimit = 20,
           sequenceChunkSize = sequenceChunkSize, // Change this to the default (10) or remove it, and the generated code will not compile
           namedAttributes = true))


   "A type with more than 22 children will compile if the sequenceChunkSize is high enough" in {
     val generated = generate(new File("integration/src/test/resources/LnAcctInfo_SLake_CType.xsd"), 20)

     (List("val subject = <LnAcctInfo_SLake xmlns=\"http://jackhenry.com/jxchange/TPG/2008\"/>",
       """scalaxb.toXML[big.XLnAcctInfo_SLake_CType](scalaxb.fromXML[big.XLnAcctInfo_SLake_CType](subject), None, Some("LnAcctInfo_SLake"), subject.scope).toString"""),
       generated) must evaluateTo("<LnAcctInfo_SLake xmlns=\"http://jackhenry.com/jxchange/TPG/2008\"/>", outdir = "./tmp")

    }
}
