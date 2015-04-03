import java.io.{File}
import scalaxb.compiler.Config


object BannoNestedSeqTest extends TestBase {
  val DEFAULT_SEQ_CHUNK_SIZE = 10 // This is the default inside scalaxb - here just for clarity

  def generate(inFile:File, sequenceChunkSize:Int = DEFAULT_SEQ_CHUNK_SIZE, wrappedTypes:List[String] = Nil) = module.process(inFile,
    Config(packageNames = Map(None -> Some("big") ),
      outdir = tmp,
      classPrefix = Some("X"),
      paramPrefix = None,
           wrappedComplexTypes = wrappedTypes,
           contentsSizeLimit = 20,
           sequenceChunkSize = sequenceChunkSize,
           namedAttributes = true))

    /*
       When run from jxchange-scalaxb, LnAcctInfo_CType's generated code will not compile.  It generates:

       lazy val lnacctinfo_ctypesequence5 = lnacctinfo_ctypesequence4.lnacctinfo_ctypesequence5

       but LnAcctInfo_CTypeSequence4 has no such field!

       It seems like it might be getting confused about the generated
       sequence types because this type has over 22 children _and_ it has nested <xsd:sequences>.  I thought that maybe
       both conditions cause the generation of overlapping LnAcctInfo_CTypeSequenceN classes.  However, it seems to work when run from
       this test.  The input xsd file has only the LNAcctInfo_CType type, and it's children are now all xsd:string.
    */

   "A type with more than 22 children and nested sequences will compile if the sequenceChunkSize is high enough" in {
     val generated = generate(new File("integration/src/test/resources/LnAcctInfo_CType.xsd"), 20)

     (List("val subject = <LnAcctInfo xmlns=\"http://jackhenry.com/jxchange/TPG/2008\"/>",
       """scalaxb.toXML[big.XLnAcctInfo_CType](scalaxb.fromXML[big.XLnAcctInfo_CType](subject), None, Some("LnAcctInfo"), subject.scope).toString"""),
       generated) must evaluateTo("<LnAcctInfo xmlns=\"http://jackhenry.com/jxchange/TPG/2008\"/>", outdir = "./tmp")

    }
}
